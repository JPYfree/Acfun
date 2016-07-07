package me.jpyfree.acfun.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/6/21.
 */
public class RecommendHot {

    public static RecommendHot parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, RecommendHot.class);
    }

    /**
     * msg : ok
     * data : {"page":{"pageNo":1,"pageSize":10,"orderBy":1,"totalCount":1694,"list":[{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21172547b5ej.jpg","slideId":20763,"releaseDate":1437549156000,"subtitle":"魔兽电影新镜头","weekday":null,"description":"","time":null,"title":"洛萨与迦罗娜镜头流出","type":0,"config":6,"url":null,"specialId":"2042003"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22125758743o.jpg","slideId":20777,"releaseDate":1437548960000,"subtitle":"生日快乐niconiconi~","weekday":null,"description":"","time":null,"title":"矢泽妮可的疯狂RAP","type":0,"config":6,"url":null,"specialId":"2016497"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/18180440kru9.jpg","slideId":20768,"releaseDate":1437548812000,"subtitle":"激燃向高同步","weekday":null,"description":"","time":null,"title":"大圣归来x龙战骑士","type":0,"config":6,"url":null,"specialId":"2035416"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/220042197c33.jpg","slideId":20766,"releaseDate":1437548791000,"subtitle":"大圣威武！！","weekday":null,"description":"","time":null,"title":"献与大圣的赞歌  The Dawn","type":0,"config":6,"url":null,"specialId":"2042863"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22143736p3kq.jpg","slideId":20792,"releaseDate":1437538448000,"subtitle":"周杰伦赞音准！","weekday":null,"description":"","time":null,"title":"马云献唱《好声音》","type":0,"config":6,"url":null,"specialId":"2043959"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22055851utuz.jpg","slideId":20789,"releaseDate":1437534965000,"subtitle":"【鸡蛋狂魔】","weekday":null,"description":"","time":null,"title":"如何在3秒内完美煎蛋","type":0,"config":6,"url":null,"specialId":"2042995"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22114659kv8v.jpg","slideId":20788,"releaseDate":1437534025000,"subtitle":"原创歌曲","weekday":null,"description":"","time":null,"title":"【围观】索尼大法好","type":0,"config":6,"url":null,"specialId":"2043538"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22100811o4bs.jpg","slideId":20765,"releaseDate":1437531281000,"subtitle":"用最美的方法让家乡重生","weekday":null,"description":"","time":null,"title":"被艺术拯救的日本乡村","type":0,"config":6,"url":null,"specialId":"2042513"},{"cover":"http://ww2.sinaimg.cn/mw690/945b7e2ajw1euap329b52j205u03c0st.jpg","slideId":20771,"releaseDate":1437531240000,"subtitle":"中国LL粉上日本电视节目","weekday":null,"description":"","time":null,"title":"为了LL登陆大霓虹","type":0,"config":6,"url":null,"specialId":"2041724"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21211214vsud.jpg","slideId":20770,"releaseDate":1437531233000,"subtitle":"吉卜力的故事","weekday":null,"description":"","time":null,"title":"宫崎骏陪我们走过的30年","type":0,"config":6,"url":null,"specialId":"2042516"}]}}
     * success : true
     * status : 200
     */
    private String msg;
    private DataEntity data;
    private boolean success;
    private int status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataEntity {
        /**
         * page : {"pageNo":1,"pageSize":10,"orderBy":1,"totalCount":1694,"list":[{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21172547b5ej.jpg","slideId":20763,"releaseDate":1437549156000,"subtitle":"魔兽电影新镜头","weekday":null,"description":"","time":null,"title":"洛萨与迦罗娜镜头流出","type":0,"config":6,"url":null,"specialId":"2042003"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22125758743o.jpg","slideId":20777,"releaseDate":1437548960000,"subtitle":"生日快乐niconiconi~","weekday":null,"description":"","time":null,"title":"矢泽妮可的疯狂RAP","type":0,"config":6,"url":null,"specialId":"2016497"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/18180440kru9.jpg","slideId":20768,"releaseDate":1437548812000,"subtitle":"激燃向高同步","weekday":null,"description":"","time":null,"title":"大圣归来x龙战骑士","type":0,"config":6,"url":null,"specialId":"2035416"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/220042197c33.jpg","slideId":20766,"releaseDate":1437548791000,"subtitle":"大圣威武！！","weekday":null,"description":"","time":null,"title":"献与大圣的赞歌  The Dawn","type":0,"config":6,"url":null,"specialId":"2042863"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22143736p3kq.jpg","slideId":20792,"releaseDate":1437538448000,"subtitle":"周杰伦赞音准！","weekday":null,"description":"","time":null,"title":"马云献唱《好声音》","type":0,"config":6,"url":null,"specialId":"2043959"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22055851utuz.jpg","slideId":20789,"releaseDate":1437534965000,"subtitle":"【鸡蛋狂魔】","weekday":null,"description":"","time":null,"title":"如何在3秒内完美煎蛋","type":0,"config":6,"url":null,"specialId":"2042995"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22114659kv8v.jpg","slideId":20788,"releaseDate":1437534025000,"subtitle":"原创歌曲","weekday":null,"description":"","time":null,"title":"【围观】索尼大法好","type":0,"config":6,"url":null,"specialId":"2043538"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22100811o4bs.jpg","slideId":20765,"releaseDate":1437531281000,"subtitle":"用最美的方法让家乡重生","weekday":null,"description":"","time":null,"title":"被艺术拯救的日本乡村","type":0,"config":6,"url":null,"specialId":"2042513"},{"cover":"http://ww2.sinaimg.cn/mw690/945b7e2ajw1euap329b52j205u03c0st.jpg","slideId":20771,"releaseDate":1437531240000,"subtitle":"中国LL粉上日本电视节目","weekday":null,"description":"","time":null,"title":"为了LL登陆大霓虹","type":0,"config":6,"url":null,"specialId":"2041724"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21211214vsud.jpg","slideId":20770,"releaseDate":1437531233000,"subtitle":"吉卜力的故事","weekday":null,"description":"","time":null,"title":"宫崎骏陪我们走过的30年","type":0,"config":6,"url":null,"specialId":"2042516"}]}
         */
        private PageEntity page;

        public PageEntity getPage() {
            return page;
        }

        public void setPage(PageEntity page) {
            this.page = page;
        }

        public static class PageEntity {
            /**
             * pageNo : 1
             * pageSize : 10
             * orderBy : 1
             * totalCount : 1694
             * list : [{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21172547b5ej.jpg","slideId":20763,"releaseDate":1437549156000,"subtitle":"魔兽电影新镜头","weekday":null,"description":"","time":null,"title":"洛萨与迦罗娜镜头流出","type":0,"config":6,"url":null,"specialId":"2042003"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22125758743o.jpg","slideId":20777,"releaseDate":1437548960000,"subtitle":"生日快乐niconiconi~","weekday":null,"description":"","time":null,"title":"矢泽妮可的疯狂RAP","type":0,"config":6,"url":null,"specialId":"2016497"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/18180440kru9.jpg","slideId":20768,"releaseDate":1437548812000,"subtitle":"激燃向高同步","weekday":null,"description":"","time":null,"title":"大圣归来x龙战骑士","type":0,"config":6,"url":null,"specialId":"2035416"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/220042197c33.jpg","slideId":20766,"releaseDate":1437548791000,"subtitle":"大圣威武！！","weekday":null,"description":"","time":null,"title":"献与大圣的赞歌  The Dawn","type":0,"config":6,"url":null,"specialId":"2042863"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22143736p3kq.jpg","slideId":20792,"releaseDate":1437538448000,"subtitle":"周杰伦赞音准！","weekday":null,"description":"","time":null,"title":"马云献唱《好声音》","type":0,"config":6,"url":null,"specialId":"2043959"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22055851utuz.jpg","slideId":20789,"releaseDate":1437534965000,"subtitle":"【鸡蛋狂魔】","weekday":null,"description":"","time":null,"title":"如何在3秒内完美煎蛋","type":0,"config":6,"url":null,"specialId":"2042995"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22114659kv8v.jpg","slideId":20788,"releaseDate":1437534025000,"subtitle":"原创歌曲","weekday":null,"description":"","time":null,"title":"【围观】索尼大法好","type":0,"config":6,"url":null,"specialId":"2043538"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22100811o4bs.jpg","slideId":20765,"releaseDate":1437531281000,"subtitle":"用最美的方法让家乡重生","weekday":null,"description":"","time":null,"title":"被艺术拯救的日本乡村","type":0,"config":6,"url":null,"specialId":"2042513"},{"cover":"http://ww2.sinaimg.cn/mw690/945b7e2ajw1euap329b52j205u03c0st.jpg","slideId":20771,"releaseDate":1437531240000,"subtitle":"中国LL粉上日本电视节目","weekday":null,"description":"","time":null,"title":"为了LL登陆大霓虹","type":0,"config":6,"url":null,"specialId":"2041724"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21211214vsud.jpg","slideId":20770,"releaseDate":1437531233000,"subtitle":"吉卜力的故事","weekday":null,"description":"","time":null,"title":"宫崎骏陪我们走过的30年","type":0,"config":6,"url":null,"specialId":"2042516"}]
             */
            private int pageNo;
            private int pageSize;
            private int orderBy;
            private int totalCount;
            private List<ListEntity> list;

            public int getPageNo() {
                return pageNo;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getOrderBy() {
                return orderBy;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public void setOrderBy(int orderBy) {
                this.orderBy = orderBy;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public static class ListEntity {
                /**
                 * cover : http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21172547b5ej.jpg
                 * slideId : 20763
                 * releaseDate : 1437549156000
                 * subtitle : 魔兽电影新镜头
                 * weekday : null
                 * description :
                 * time : null
                 * title : 洛萨与迦罗娜镜头流出
                 * type : 0
                 * config : 6
                 * url : null
                 * specialId : 2042003
                 */
                private String cover;
                private int slideId;
                private long releaseDate;
                private String subtitle;
                private String weekday;
                private String description;
                private String time;
                private String title;
                private int type;
                private int config;
                private String url;
                private String specialId;

                public String getCover() {
                    return cover;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public int getSlideId() {
                    return slideId;
                }

                public void setSlideId(int slideId) {
                    this.slideId = slideId;
                }

                public long getReleaseDate() {
                    return releaseDate;
                }

                public void setReleaseDate(long releaseDate) {
                    this.releaseDate = releaseDate;
                }

                public String getSubtitle() {
                    return subtitle;
                }

                public void setSubtitle(String subtitle) {
                    this.subtitle = subtitle;
                }

                public String getWeekday() {
                    return weekday;
                }

                public void setWeekday(String weekday) {
                    this.weekday = weekday;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getConfig() {
                    return config;
                }

                public void setConfig(int config) {
                    this.config = config;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getSpecialId() {
                    return specialId;
                }

                public void setSpecialId(String specialId) {
                    this.specialId = specialId;
                }
            }
        }
    }
}
