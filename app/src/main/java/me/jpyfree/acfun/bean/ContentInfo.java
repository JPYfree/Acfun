package me.jpyfree.acfun.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class ContentInfo {
    /**
     * status : 200
     * data : {"fullContent":{"tags":["亡国的阿基德"],"toplevel":0,"channelId":67,"videos":[{"startTime":0,"sourceType":"letv","time":3602,"name":"F宅","danmakuId":2463027,"videoId":2463027,"type":"letv","commentId":2463027,"sourceId":"311043eae0"},{"startTime":0,"sourceType":"letv","time":3602,"name":"白恋","danmakuId":2463291,"videoId":2463291,"type":"letv","commentId":2463291,"sourceId":"36fc8b71e5"}],"isArticle":0,"contentId":2069095,"viewOnly":0,"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201508/02232914yqta.jpg","title":"【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】","releaseDate":1438526006000,"description":"【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】","views":4412,"stows":57,"user":{"username":"零元帅","userId":1370061,"userImg":"http://static.acfun.mm111.net/dotnet/20120923/style/image/avatar.jpg"},"isRecommend":0,"comments":24}}
     * msg : 操作成功
     * success : true
     */
    private int status;
    private DataEntity data;
    private String msg;
    private boolean success;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataEntity {
        /**
         * fullContent : {"tags":["亡国的阿基德"],"toplevel":0,"channelId":67,"videos":[{"startTime":0,"sourceType":"letv","time":3602,"name":"F宅","danmakuId":2463027,"videoId":2463027,"type":"letv","commentId":2463027,"sourceId":"311043eae0"},{"startTime":0,"sourceType":"letv","time":3602,"name":"白恋","danmakuId":2463291,"videoId":2463291,"type":"letv","commentId":2463291,"sourceId":"36fc8b71e5"}],"isArticle":0,"contentId":2069095,"viewOnly":0,"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201508/02232914yqta.jpg","title":"【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】","releaseDate":1438526006000,"description":"【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】","views":4412,"stows":57,"user":{"username":"零元帅","userId":1370061,"userImg":"http://static.acfun.mm111.net/dotnet/20120923/style/image/avatar.jpg"},"isRecommend":0,"comments":24}
         */
        private FullContentEntity fullContent;

        public FullContentEntity getFullContent() {
            return fullContent;
        }

        public void setFullContent(FullContentEntity fullContent) {
            this.fullContent = fullContent;
        }

        public static class FullContentEntity {
            /**
             * tags : ["亡国的阿基德"]
             * toplevel : 0
             * channelId : 67
             * videos : [{"startTime":0,"sourceType":"letv","time":3602,"name":"F宅","danmakuId":2463027,"videoId":2463027,"type":"letv","commentId":2463027,"sourceId":"311043eae0"},{"startTime":0,"sourceType":"letv","time":3602,"name":"白恋","danmakuId":2463291,"videoId":2463291,"type":"letv","commentId":2463291,"sourceId":"36fc8b71e5"}]
             * isArticle : 0
             * contentId : 2069095
             * viewOnly : 0
             * cover : http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201508/02232914yqta.jpg
             * title : 【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】
             * releaseDate : 1438526006000
             * description : 【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】
             * views : 4412
             * stows : 57
             * user : {"username":"零元帅","userId":1370061,"userImg":"http://static.acfun.mm111.net/dotnet/20120923/style/image/avatar.jpg"}
             * isRecommend : 0
             * comments : 24
             */
            private List<String> tags;
            private int toplevel;
            private int channelId;
            private List<VideosEntity> videos;
            private int isArticle;
            private String contentId;
            private int viewOnly;
            private String cover;
            private String title;
            private long releaseDate;
            private String description;
            private int views;
            private int stows;
            private UserEntity user;
            private int isRecommend;
            private int comments;

            public List<String> getTags() {
                return tags;
            }

            public void setTags(List<String> tags) {
                this.tags = tags;
            }

            public int getToplevel() {
                return toplevel;
            }

            public void setToplevel(int toplevel) {
                this.toplevel = toplevel;
            }

            public int getChannelId() {
                return channelId;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }

            public List<VideosEntity> getVideos() {
                return videos;
            }

            public void setVideos(List<VideosEntity> videos) {
                this.videos = videos;
            }

            public int getIsArticle() {
                return isArticle;
            }

            public void setIsArticle(int isArticle) {
                this.isArticle = isArticle;
            }

            public String getContentId() {
                return contentId;
            }

            public void setContentId(String contentId) {
                this.contentId = contentId;
            }

            public int getViewOnly() {
                return viewOnly;
            }

            public void setViewOnly(int viewOnly) {
                this.viewOnly = viewOnly;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public long getReleaseDate() {
                return releaseDate;
            }

            public void setReleaseDate(long releaseDate) {
                this.releaseDate = releaseDate;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public int getStows() {
                return stows;
            }

            public void setStows(int stows) {
                this.stows = stows;
            }

            public UserEntity getUser() {
                return user;
            }

            public void setUser(UserEntity user) {
                this.user = user;
            }

            public int getIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(int isRecommend) {
                this.isRecommend = isRecommend;
            }

            public int getComments() {
                return comments;
            }

            public void setComments(int comments) {
                this.comments = comments;
            }

            public static class VideosEntity implements Parcelable{
                /**
                 * startTime : 0
                 * sourceType : letv
                 * time : 3602
                 * name : F宅
                 * danmakuId : 2463027
                 * videoId : 2463027
                 * type : letv
                 * commentId : 2463027
                 * sourceId : 311043eae0
                 */
                private int startTime;
                private String sourceType;
                private int time;
                private String name;
                private String danmakuId;
                private String videoId;
                private String type;
                private String commentId;
                private String sourceId;
                private String videoTitle;

                public String getVideoTitle() {
                    return videoTitle;
                }

                public void setVideoTitle(String videoTitle) {
                    this.videoTitle = videoTitle;
                }

                public int getStartTime() {
                    return startTime;
                }

                public void setStartTime(int startTime) {
                    this.startTime = startTime;
                }

                public String getSourceType() {
                    return sourceType;
                }

                public void setSourceType(String sourceType) {
                    this.sourceType = sourceType;
                }

                public int getTime() {
                    return time;
                }

                public void setTime(int time) {
                    this.time = time;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDanmakuId() {
                    return danmakuId;
                }

                public void setDanmakuId(String danmakuId) {
                    this.danmakuId = danmakuId;
                }

                public String getVideoId() {
                    return videoId;
                }

                public void setVideoId(String videoId) {
                    this.videoId = videoId;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getCommentId() {
                    return commentId;
                }

                public void setCommentId(String commentId) {
                    this.commentId = commentId;
                }

                public String getSourceId() {
                    return sourceId;
                }

                public void setSourceId(String sourceId) {
                    this.sourceId = sourceId;
                }

                protected VideosEntity(Parcel in) {
                    this.startTime = in.readInt();
                    this.sourceType = in.readString();
                    this.time = in.readInt();
                    this.name = in.readString();
                    this.danmakuId = in.readString();
                    this.videoId = in.readString();
                    this.type = in.readString();
                    this.commentId = in.readString();
                    this.sourceId = in.readString();
                    this.videoTitle = in.readString();
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.startTime);
                }

                public static final Parcelable.Creator<VideosEntity> CREATOR = new Creator<VideosEntity>() {
                    @Override
                    public VideosEntity createFromParcel(Parcel source) {
                        return new VideosEntity(source);
                    }

                    @Override
                    public VideosEntity[] newArray(int size) {
                        return new VideosEntity[size];
                    }
                };
            }

            public class UserEntity {
                /**
                 * username : 零元帅
                 * userId : 1370061
                 * userImg : http://static.acfun.mm111.net/dotnet/20120923/style/image/avatar.jpg
                 */
                private String username;
                private int userId;
                private String userImg;

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }

                public String getUserImg() {
                    return userImg;
                }

                public void setUserImg(String userImg) {
                    this.userImg = userImg;
                }
            }
        }
    }
}
