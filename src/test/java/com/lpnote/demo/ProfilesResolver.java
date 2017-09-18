package com.lpnote.demo;

import org.springframework.test.context.ActiveProfilesResolver;

/**
 * Created by luopeng on 2017/9/18.
 */
public class ProfilesResolver implements ActiveProfilesResolver {
    @Override
    public String[] resolve(Class<?> aClass) {
        String activeProfiles = System.getProperty("spring.profiles.active");
        return new String[] { activeProfiles != null ? activeProfiles : "dev" };
    }
}

