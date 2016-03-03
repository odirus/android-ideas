package me.odirus.ideas;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.*;
import com.avos.avoscloud.search.AVSearchQuery;

import java.util.Collections;
import java.util.List;

/**
 * Created by lzw on 14-9-12.
 */
public class AVService {
    public static void AVInit(Context ctx) {
        // 注册子类
        AVObject.registerSubclass(me.odirus.ideas.Todo.class);
        AVOSCloud.setDebugLogEnabled(true);
        // 初始化应用 Id 和 应用 Key，您可以在应用设置菜单里找到这些信息
        AVOSCloud.initialize(ctx, "wl6q90kma7mioh0kz8azmdnye9ubfabo9efcufnqofmp155z",
                "xtpt6zbcqy7hitxrc9ee1ja0m8cjjmgtt0x9nce0tmljdq9r");
        // 启用崩溃错误报告
        AVAnalytics.enableCrashReport(ctx, true);
        AVOSCloud.setLastModifyEnabled(true);
    }

    public static void fetchTodoById(String objectId, GetCallback<AVObject> getCallback) {
        me.odirus.ideas.Todo todo = new me.odirus.ideas.Todo();
        todo.setObjectId(objectId);
        // 通过Fetch获取content内容
        todo.fetchInBackground(getCallback);
    }

    public static void createOrUpdateTodo(String objectId, String content, SaveCallback saveCallback) {
        final me.odirus.ideas.Todo todo = new me.odirus.ideas.Todo();
        if (!TextUtils.isEmpty(objectId)) {
            // 如果存在objectId，保存会变成更新操作。
            todo.setObjectId(objectId);
        }
        todo.setContent(content);
        // 异步保存
        todo.saveInBackground(saveCallback);
    }

    public static List<me.odirus.ideas.Todo> findTodos() {
        // 查询当前Todo列表
        AVQuery<me.odirus.ideas.Todo> query = AVQuery.getQuery(me.odirus.ideas.Todo.class);
        // 按照更新时间降序排序
        query.orderByDescending("updatedAt");
        // 最大返回1000条
        query.limit(1000);
        try {
            return query.find();
        } catch (AVException exception) {
            Log.e("tag", "Query todos failed.", exception);
            return Collections.emptyList();
        }
    }

    public static void searchQuery(String inputSearch) {
        AVSearchQuery searchQuery = new AVSearchQuery(inputSearch);
        searchQuery.search();
    }
}
