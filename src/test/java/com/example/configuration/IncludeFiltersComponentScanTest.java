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
    public void test_find_beans_which_start_with_Child_using_child_regex_pattern() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ProperRegexPatternPrefixIncludeFiltersConfiguration.class);
        Assert.assertNotNull(ctx.getBean("childService"));
        Assert.assertNotNull(ctx.getBean("childComponent"));
        Assert.assertNotNull(ctx.getBean("childDao"));
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void test_find_beans_which_start_with_Parent_using_child_regex_pattern() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ProperRegexPatternPrefixIncludeFiltersConfiguration.class);
        ctx.getBean("parentComponent");
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void test_find_beans_which_start_with_Child_using_ant_like_pattern() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AntLikePatternPrefixIncludeFiltersConfiguration.class);
        ctx.getBean("childService");
    }

    @Test
    public void test_find_beans_which_start_with_Child_using_brackets_pattern() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BracketsPatternPrefixIncludeFiltersConfiguration.class);
        Assert.assertNotNull(ctx.getBean("childService"));
        //It should throw an exception, but it does not
        Assert.assertNotNull(ctx.getBean("parentComponent"));
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

@Configuration
@ComponentScan(basePackages = "com.example",
        useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.example.*.[Child]*")})
class BracketsPatternPrefixIncludeFiltersConfiguration { }

