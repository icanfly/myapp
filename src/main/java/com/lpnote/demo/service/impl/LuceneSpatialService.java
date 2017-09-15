package com.lpnote.demo.service.impl;

import com.google.common.collect.Lists;
import com.lpnote.demo.common.util.JacksonFootTruckMapper;
import com.lpnote.demo.entity.FootTruck;
import com.lpnote.demo.entity.FootTruckQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.spatial.SpatialStrategy;
import org.apache.lucene.spatial.prefix.RecursivePrefixTreeStrategy;
import org.apache.lucene.spatial.prefix.tree.GeohashPrefixTree;
import org.apache.lucene.spatial.prefix.tree.SpatialPrefixTree;
import org.apache.lucene.spatial.query.SpatialArgs;
import org.apache.lucene.spatial.query.SpatialOperation;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.Shape;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by luopeng on 2017/9/14.
 */
@Service
public class LuceneSpatialService {

    /**
     * Spatial4j上下文
     * 1: SpatialContext初始化可由SpatialContextFactory配置
     * 2: SpatialContext属性
     * DistanceCalculator(默认使用GeodesicSphereDistCalc.Haversine,将地球视为标准球体)
     * ShapeFactory(默认使用ShapeFactoryImpl)
     * Rectangle(构建经纬度空间:RectangleImpl(-180, 180, -90, 90, this))
     * BinaryCodec()
     */
    private SpatialContext ctx;

    /**
     * 索引和查询模型的策略接口
     */
    private SpatialStrategy strategy;

    /**
     * 索引存储目录
     */
    private Directory directory;

    private IndexReader indexReader;
    private IndexSearcher indexSearcher;
    private IndexWriter indexWriter;

    @PostConstruct
    protected void init() throws Exception {
        /**
         * SpatialContext也可以通过SpatialContextFactory工厂类来构建
         * */
        this.ctx = SpatialContext.GEO;

        /**
         * 网格最大11层或Geo Hash的精度
         * 1: SpatialPrefixTree定义的Geo Hash最大精度为24
         * 2: GeohashUtils定义类经纬度到Geo Hash值公用方法
         * */
        SpatialPrefixTree spatialPrefixTree = new GeohashPrefixTree(ctx, 11);

        /**
         * 索引和搜索的策略接口,两个主要实现类
         * 1: RecursivePrefixTreeStrategy(支持任何Shape的索引和检索)
         * 2: TermQueryPrefixTreeStrategy(仅支持Point Shape)
         * 上述两个类继承PrefixTreeStrategy(有使用缓存)
         * */
        this.strategy = new RecursivePrefixTreeStrategy(spatialPrefixTree, "location");
        // 初始化索引目录
        this.directory = new RAMDirectory();

        open();
    }

    @PreDestroy
    protected void destroy() throws IOException {
        close();
    }

    private void close() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
        if (indexReader != null) {
            indexReader.close();
        }
        if (directory != null) {
            directory.close();
        }
    }

    protected void createIndex() throws Exception {
        InputStream is = null;
        try{
            is  = this.getClass().getResourceAsStream("/6a9r-agq8.json");
            List<FootTruck> footTrucks = JacksonFootTruckMapper.deserialize(is);
            indexWriter.addDocuments(newSampleDocument(ctx, strategy, footTrucks));
            indexWriter.flush();
            indexWriter.commit();
        }finally {
            if(is != null){
                is.close();
            }
        }
    }

    protected void open() throws Exception {
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        indexWriter = new IndexWriter(directory, config);

        createIndex();

        indexReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(indexReader);
    }

    /**
     * 创建Document索引对象
     */
    protected List<Document> newSampleDocument(SpatialContext ctx, SpatialStrategy strategy, List<FootTruck> footTrucks) {
        List<Document> documents = Lists.newLinkedList(footTrucks.stream().filter(ft ->
                ft.getLatitude() != 0.d && ft.getLongitude() != 0.d
        ).map(ft -> {
            Document doc = new Document();
            doc.add(new StoredField("address", getValue(ft.getAddress())));
            doc.add(new StoredField("applicant", getValue(ft.getApplicant())));
            doc.add(new StoredField("dayshours", getValue(ft.getDayshours())));
            doc.add(new StoredField("expirationdate", getValue(ft.getExpirationDate())));
            doc.add(new StoredField("items", getValue(ft.getItems())));
            doc.add(new StoredField("latitude", ft.getLatitude()));
            doc.add(new StoredField("longitude", ft.getLongitude()));
            Shape shape = null;
            /**
             * 对小于MaxLevel的Geo Hash构建Field(IndexType[indexed,tokenized,omitNorms])
             * */
            Field[] fields = strategy.createIndexableFields((shape = ctx.getShapeFactory()
                    .pointXY(ft.getLongitude(), ft.getLatitude())));
            for (Field field : fields) {
                doc.add(field);
            }
            Point pt = (Point) shape;
            doc.add(new StoredField(strategy.getFieldName(), pt.getX() + "," + pt.getY()));
            return doc;
        }).collect(Collectors.toList()));
        return documents;
    }

    private String getValue(String value) {
        return getValue(value, "N/A");
    }

    private String getValue(String value, String defaultValue) {
        return StringUtils.isNotBlank(value) ? value : defaultValue;
    }

    public List<FootTruck> search(FootTruckQuery ftQuery) throws Exception {

        SpatialArgs args = new SpatialArgs(SpatialOperation.Intersects,
                ctx.getShapeFactory().circle(ftQuery.getLongitude(), ftQuery.getLatitude(),
                        DistanceUtils.dist2Degrees(((double) ftQuery.getMeters()) / 1000, DistanceUtils.EARTH_MEAN_RADIUS_KM)));
        Query query = strategy.makeQuery(args);

        /**
         * 定义坐标点(x,y)即(经度,纬度)即当前用户所在地点(烟台)
         * */
        Point pt = ctx.getShapeFactory().pointXY(ftQuery.getLongitude(), ftQuery.getLatitude());

        /**
         * 计算当前用户所在坐标点与索引坐标点中心之间的距离即当前用户地点与每个待匹配地点之间的距离,DEG_TO_KM表示以KM为单位
         * 对Field(name=location)字段检索
         * */
        ValueSource valueSource = strategy.makeDistanceValueSource(pt, DistanceUtils.DEG_TO_KM);

        /**
         * 根据命中点与当前位置坐标点的距离远近降序排,距离数字大的排在前面,false表示降序,true表示升序
         * */
        Sort distSort = new Sort(valueSource.getSortField(false)).rewrite(indexSearcher);
        TopDocs topDocs = indexSearcher.search(query, ftQuery.getQuerySize(), distSort);

        List<FootTruck> footTrucks = Lists.newLinkedList();

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = indexSearcher.doc(docId);
            FootTruck footTruck = new FootTruck();
            footTruck.setAddress(document.getField("address").stringValue());
            footTruck.setApplicant(document.getField("applicant").stringValue());
            footTruck.setDayshours(document.getField("dayshours").stringValue());
            footTruck.setExpirationDate(document.getField("expirationdate").stringValue());
            footTruck.setItems(document.getField("items").stringValue());
            footTruck.setLatitude(document.getField("latitude").numericValue().doubleValue());
            footTruck.setLongitude(document.getField("longitude").numericValue().doubleValue());
            footTrucks.add(footTruck);
        }

        return footTrucks;
    }


}
