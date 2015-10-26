package cn.itcast.oa.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class QueryParamList
{

    public QueryParamList()
    {
        params = new ArrayList();
    }

    public void addParam(String name, Object value)
    {
        QueryParam param = new QueryParam();
        param.setName(name);
        param.setValue(value);
        params.add(param);
    }

    public void addParam(String name, Object value, String relation)
    {
        QueryParam param = new QueryParam();
        param.setName(name);
        param.setValue(value);
        param.setRelation(relation);
        params.add(param);
    }

    public void addParam(QueryParam param)
    {
        if(param == null || param.getName() == null || param.getValue() == null)
        {
            return;
        } else
        {
            params.add(param);
            return;
        }
    }

    public void addParamList(QueryParamList aparams)
    {
        if(aparams == null || aparams.getParams().size() <= 0)
            return;
        List list = aparams.getParams();
        for(int i = 0; i < list.size(); i++)
        {
            QueryParam param = (QueryParam)list.get(i);
            params.add(param);
        }

    }

    public List getParams()
    {
        return params;
    }

    public void setParams(List params)
    {
        this.params = params;
    }

    public int size()
    {
        return params.size();
    }

    public QueryParam get(int index)
    {
        return (QueryParam)params.get(index);
    }

    public QueryParam get(String name)
    {
        if(name == null)
            return null;
        for(Iterator iterator = params.iterator(); iterator.hasNext();)
        {
            QueryParam param = (QueryParam)iterator.next();
            if(name.equals(param.getName()))
                return param;
        }

        return null;
    }

    public boolean remove(String name)
    {
        if(name == null)
            return false;
        for(Iterator iterator = params.iterator(); iterator.hasNext();)
        {
            QueryParam param = (QueryParam)iterator.next();
            if(name.equals(param.getName()))
            {
                params.remove(param);
                return true;
            }
        }

        return false;
    }

    private List params;
}
