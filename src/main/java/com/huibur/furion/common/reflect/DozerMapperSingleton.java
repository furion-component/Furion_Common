package com.huibur.furion.common.reflect;


import com.github.pagehelper.PageInfo;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class DozerMapperSingleton {
    private Mapper mapper;

    private static class SingletonHolder {
        private static DozerMapperSingleton instance = new DozerMapperSingleton();
        // private static Mapper instance = new DozerBeanMapper();
    }

    private DozerMapperSingleton() {
        mapper = new DozerBeanMapper();
    }

    public static DozerMapperSingleton getInstance() {
        return DozerMapperSingleton.SingletonHolder.instance;
    }

    public <T> T map(Object source, Class<T> destinationClass) {
        if (null == source) {
            return null;
        }
        return (T) mapper.map(source, destinationClass);
    }

    public <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List destinationList = new ArrayList();
        if (null == sourceList) {
            return destinationList;
        }
        for (Iterator i$ = sourceList.iterator(); i$.hasNext(); ) {
            Object sourceObject = i$.next();
            Object destinationObject = mapper.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    public void copy(Object source, Object destinationObject) {
        mapper.map(source, destinationObject);
    }

    public <T> PageInfo<T> mapPage(PageInfo source, Class<T> destinationClass) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(source.getPageNum());
        pageInfo.setPageSize(source.getPageSize());
        pageInfo.setStartRow(source.getStartRow());
        pageInfo.setEndRow(source.getEndRow());
        pageInfo.setPages(source.getPages());
        pageInfo.setTotal(source.getTotal());
        pageInfo.setList(mapList(source.getList(), destinationClass));
        return pageInfo;
    }
}
