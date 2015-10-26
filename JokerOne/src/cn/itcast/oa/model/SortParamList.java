package cn.itcast.oa.model;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.haiyisoft.ep.common.model:
//            SortParam

public class SortParamList
{

    public SortParamList()
    {
        params = new ArrayList();
    }

    public void addParam(SortParam param)
    {
        if(param == null || param.getSortProperty() == null || "".equals(param.getSortProperty()))
        {
            return;
        } else
        {
            params.add(param);
            return;
        }
    }

    public void addParam(String property, String type)
    {
        SortParam sortParam = new SortParam(property, type);
        params.add(sortParam);
    }

    public void addParam(String property, String type, String alias)
    {
        SortParam sortParam = new SortParam(property, type, alias);
        params.add(sortParam);
    }

    public List getParams()
    {
        return params;
    }

    public void setParams(List params)
    {
        this.params = params;
    }

    private List params;
}