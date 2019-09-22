package com.example.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class IncludeFiltersComponentScanTest {

    @Test
    public void test_find_beans_which_start_with_Child_using_regex_pattern() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ProperRegexPatternPrefixIncludeFiltersConfiguration.class);
        Assert.assertNotNull(ctx.getBean("childService"));
        Assert.assertNotNull(ctx.getBean("childComponent"));
        Assert.assertNotNull(ctx.getBean("childDao"));
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void test_find_beans_which_start_with_Child_using_ant_like_pattern() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AntLikePatternPrefixIncludeFiltersConfiguration.class);
        ctx.getBean("childService");
    }


}

@Configuration
@ComponentScan(basePackages = "com.example",
        useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.example.*.Child.*")})
class ProperRegexPatternPrefixIncludeFiltersConfiguration { }

@Configuration
@ComponentScan(basePackages = "com.example",
        useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.example.*.Child*")})
class AntLikePatternPrefixIncludeFiltersConfiguration { }

