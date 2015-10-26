package cn.itcast.oa.model;

public class SortParam
{

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public SortParam()
    {
        sortType = "ASC";
    }

    public SortParam(String property, String type)
    {
        sortType = "ASC";
        sortProperty = property;
        sortType = type;
    }

    public SortParam(String property, String type, String alias)
    {
        sortType = "ASC";
        sortProperty = property;
        sortType = type;
        this.alias = alias;
    }

    public String getSortProperty()
    {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty)
    {
        this.sortProperty = sortProperty;
    }

    public String getSortType()
    {
        return sortType;
    }

    public void setSortType(String sortType)
    {
        this.sortType = sortType;
    }

    public static final String SORT_TYPE_ASCENDING = "ASC";
    public static final String SORT_TYPE_DESCENDING = "DESC";
    private String sortProperty;
    private String sortType;
    private String alias;
}
