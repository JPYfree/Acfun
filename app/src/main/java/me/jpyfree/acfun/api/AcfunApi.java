package me.jpyfree.acfun.api;

import java.util.HashMap;
import java.util.Map;

import me.jpyfree.acfun.bean.Bangumi;
import me.jpyfree.acfun.bean.ContentInfo;
import me.jpyfree.acfun.bean.ContentReply;
import me.jpyfree.acfun.bean.Essay;
import me.jpyfree.acfun.bean.RecommendBanner;
import me.jpyfree.acfun.bean.RecommendHot;
import me.jpyfree.acfun.bean.RecommendOther;
import me.jpyfree.acfun.config.RetrofitManager;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/21.
 */
public class AcfunApi {

    /**
     * @return 都有且不变的url参数
     * */
    public static HashMap<String, String> buildBaseMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put(AcfunString.APP_VERSION, AcfunString.APP_NUM);
        map.put(AcfunString.SYS_NAME, AcfunString.SYS_NAME_ANDROID);
        map.put(AcfunString.SYS_VERSION, AcfunString.SYS_VERSION_ANDROID);
        map.put(AcfunString.RESOLUTION, AcfunString.RESOLUTION_WIDTH_HEIGHT);
        map.put(AcfunString.MARKET, AcfunString.MARKET_NAME);
        return map;
    }

    /**
     * http://api.acfun.tv/apiserver
     * /recommend/list
     * ?app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 首页横幅
     */
    public static HashMap<String, String> buildRecommendBannerUrl() {
        return buildBaseMap();
    }

    /**
     * http://api.acfun.tv/apiserver
     * /recommend/page
     * ?pageSize=10&pageNo=1
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @param pageNo 返回页数
     * @return 热门焦点
     */
    public static HashMap<String, String> buildRecommendHotUrl(String pageNo) {
        HashMap<String, String> map = buildBaseMap();
        map.put(AcfunString.PAGE_SIZE, AcfunString.PAGE_SIZE_NUM_10);
        map.put(AcfunString.PAGE_NO, pageNo);
        return map;
    }

    /**
     * http://api.acfun.tv/apiserver/
     * content/channel
     * ?channelIds=106,107,108,109,67,120
     * &pageSize=20&pageNo=1
     * &orderBy=5
     * &range=604800000
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @param channelId 根据不同的channelId
     * @param orderBy   按照什么来排序来还返回 最后回复 最新发布等
     * @param range     返回数据是多少时间内统计的 一周 一月 三月 总共
     * @return 返回不同的分区信息
     */
    public static HashMap<String, String> buildRecommendOtherUrl(String channelId, String orderBy, String range) {
        HashMap<String, String> map = buildBaseMap();
        map.put(AcfunString.CHANNEL_IDS, channelId);
        map.put(AcfunString.PAGE_SIZE, AcfunString.PAGE_SIZE_NUM_20);
        map.put(AcfunString.PAGE_NO, AcfunString.PAGE_NO_NUM_1);
        map.put(AcfunString.ORDER_BY, orderBy);
        map.put(AcfunString.RANGE, range);
        return map;
    }

    public interface onRecommend {

        @GET(AcfunString.RECOMMEND_LIST)
        Observable<RecommendBanner> onRecommendBannerResult(@QueryMap() Map<String, String> options);

        @GET(AcfunString.RECOMMEND_PAGE)
        Observable<RecommendHot> onRecommendHotResult(@QueryMap() Map<String, String> options);

        @GET(AcfunString.CONTENT_CHANNEL)
        Observable<RecommendOther> onRecommendOtherResult(@QueryMap() Map<String, String> options);
    }

    public static onRecommend getRecommend() {
        return RetrofitManager.getAcfunApiServer().create(onRecommend.class);
    }

    /**
     * http://api.acfun.tv/apiserver
     * /content/channel
     * ?channelIds=67
     * &pageSize=20&pageNo=1
     * &orderBy=7
     * &range=604800000
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 分区内不同板块数据
     */
    public static HashMap<String, String> buildPartitionUrl(String channelIds, String pageSize, String pageNo,
                                                            String orderBy, String range) {
        HashMap<String, String> map = buildBaseMap();
        map.put(AcfunString.CHANNEL_IDS, channelIds);
        map.put(AcfunString.PAGE_SIZE, pageSize);
        map.put(AcfunString.PAGE_NO, pageNo);
        map.put(AcfunString.ORDER_BY, orderBy);
        map.put(AcfunString.RANGE, range);
        return map;
    }

    public interface onPartition {

        @GET(AcfunString.CONTENT_CHANNEL)
        Observable<RecommendOther> onOtherResult(@QueryMap() Map<String, String> options);

        @GET(AcfunString.CONTENT_CHANNEL)
        Observable<Essay> onEssayResult(@QueryMap() Map<String, String> options);
    }

    public static onPartition getPartition() {
        return RetrofitManager.getAcfunApiServer().create(onPartition.class);
    }

    /**
     * http://api.acfun.tv/apiserver
     * /content/info?contentId=2069095
     * &version=2
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 视频具体信息
     */
    public static HashMap<String, String> buildContentInfoUrl(String contentId) {
        HashMap<String, String> map = buildBaseMap();
        map.put(AcfunString.VERSION, AcfunString.VERSION_NUM_2);
        map.put(AcfunString.CONTENT_ID, contentId);
        return map;
    }

    public interface onContentInfo {

        @GET(AcfunString.CONTENT_INFO)
        Observable<ContentInfo> onInfoResult(@QueryMap() Map<String, String> options);
    }

    public static onContentInfo getContentInfo() {
        return RetrofitManager.getAcfunApiServer().create(onContentInfo.class);
    }

    /**
     * http://www.acfun.tv
     * /comment/content/list?version=4&contentId=2086956
     * &pageSize=20&pageNo=1
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 视频的评论信息
     */
    public static HashMap<String, String> buildContentReplyUrl(String contentId, String pageSize, String pageNo) {
        HashMap<String, String> map = buildBaseMap();
        map.put(AcfunString.VERSION, AcfunString.VERSION_NUM_4);
        map.put(AcfunString.CONTENT_ID, contentId);
        map.put(AcfunString.PAGE_SIZE, pageSize);
        map.put(AcfunString.PAGE_NO, pageNo);
        return map;
    }

    public interface onContentReply {

        @GET(AcfunString.CONTENT_REPLY)
        Observable<ContentReply> onReplyResult(@QueryMap() Map<String, String> options);
    }

    public static onContentReply getContentReply() {
        return RetrofitManager.getAcfunTv().create(onContentReply.class);
    }

    /**
     * http://icao.acfun.tv
     * /bangumi/week
     * ?bangumiTypes=1
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 每周新番时间表
     */
    public static HashMap<String, String> buildBangumiUrl(String types) {
        HashMap<String, String> map = buildBaseMap();
        map.put(AcfunString.BANGUMI_TYPES, types);
        return map;
    }

    public interface onBangumi {

        @GET(AcfunString.BANGUMI_WEEK)
        Observable<Bangumi> onBangumiResult(@QueryMap() Map<String, String> options);
    }

    public static onBangumi getBangumi() {
        return RetrofitManager.getAcfunICao().create(onBangumi.class);
    }

    /**
     * http://api.acfun.tv/apiserver/
     * content/rank
     * ?pageSize=20&pageNo=1
     * &channelIds=96,97,98,99,100,93,94,95,101,102,103,104,105,86,87,88,89,121,106,107,108,109,67,120,90,91,92,122,83,84,85,82
     * &app_version=118&sys_name=android&sys_version=5.1.1&market=m360&resolution=1080x1776
     *
     * @return 视频排行
     */
    public static HashMap<String, String> buildRankingUrl(String pageNo, String channelIds) {
        HashMap<String, String> map = buildBaseMap();
        map.put(AcfunString.PAGE_SIZE, AcfunString.PAGE_SIZE_NUM_20);
        map.put(AcfunString.PAGE_NO, pageNo);
        map.put(AcfunString.CHANNEL_IDS, channelIds);
        return map;
    }

    public interface onRanking {

        @GET(AcfunString.CONTENT_RANK)
        Observable<RecommendOther> onRankingResult(@QueryMap() Map<String, String> options);
    }

    public static onRanking getRanking() {
        return RetrofitManager.getAcfunApiServer().create(onRanking.class);
    }
}
