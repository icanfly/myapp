<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="/static/css/index.css" type="text/css">
    <link rel="stylesheet" href="/static/css/loading.css" type="text/css"/>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=oqX2VPKvW3G9mEGENhoRclajvk8El4Bv"></script>
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/index.js"></script>
    <script type="text/javascript" src="/static/js/layer/layer.js"></script>
    <script type="text/javascript" src="/static/js/loading.js"></script>
    <title>Foot Truck Demo</title>
</head>
<body>
<h1 id="head">
    Foot Truck Demo <a href="https://github.com/icanfly/myapp" target="_blank" style="float:left;margin-right: 20px">
    <svg height="32" width="32" class="octicon
    octicon-mark-github" viewBox="0 0 16 16" version="1.1" aria-hidden="true"><path fill-rule="evenodd" d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0 0 16 8c0-4.42-3.58-8-8-8z"></path></svg></a>
</h1>
<div id="container">
    <div id="leftSide">
        <div>
            <h2>使用说明</h2>
            <span>在右图中拖动地图，并使用鼠标右键菜单<b style="color:green">"搜索附近餐车"</b>对附近的FootTruck进行搜索，搜索范围大小受参数<b
                    style="color:green">'搜索范围'</b>控制，搜索结果集返回量受参数<b style="color:green">'返回数量'</b>控制</span>
        </div>
        <div>
            <h2>搜索结果说明</h2>
            <li>如果没有满足条件的搜索结果，则在页面上会弹出提示窗提示未找到数据。</li>
            <li>如果查询有数据，则会在地图上以<b style="color:red">红色标记</b>的形式标出，用鼠标双击红色标记会弹出信息窗口显示该餐车的一些信息，如<b
                    style="color:green">餐车名称，餐车地址，餐车供应食物类别等。</b></li>
        </div>
        <div>
            <h2>参数调节</h2>
            <div>
                <div>数据来源：<input id="onlineRadio" type="radio" name="source" value="online">实时
                    <input id="offlineRadio" type="radio" name="source" value="offline" checked>离线（至2017-09-14有效数据）
                </div>
                <div>搜索范围: <input id="meters" type="text" value="1000">（默认1000，最大2000，单位:米）</div>
                <div>返回数量: <input id="querySize" type="text" value="10">（默认10，最大20个）</div>
            </div>
        </div>
    </div>
    <div id="mapDiv"></div>
</div>
</body>
</html>
