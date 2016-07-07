package me.jpyfree.acfun.config;

import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by Administrator on 2016/6/20.
 */
public class FrescoConfig {
    //分配的可用内存
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    //使用的缓存数量
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
    //小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 5 * ByteConstants.MB;
    //小图低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 10 * ByteConstants.MB;
    //小图磁盘缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_CACHE_SIZE = 20 * ByteConstants.MB;
    //默认图极低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_VERY_LOW_SIZE = 10 * ByteConstants.MB;
    //默认图低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_LOW_SIZE = 30 * ByteConstants.MB;
    //默认图磁盘缓存的最大值
    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;
    //小图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "picture_small";
    //默认图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_CACHE_DIR = "picture";

    private static ImagePipelineConfig imagePipelineConfig;

    /**
     * 初始化,使唯一
     * */


    /**
     * 初始化
     * */

}
