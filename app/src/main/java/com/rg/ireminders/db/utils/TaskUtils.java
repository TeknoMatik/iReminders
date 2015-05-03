package com.rg.ireminders.db.utils;

import android.content.Context;
import com.rg.ireminders.db.entities.Task;
import com.rg.ireminders.db.entities.TaskList;
import com.rg.ireminders.db.utils.impl.TaskUtilsImpl;
import java.util.List;

/**
 * Created by rustamgaifullin on 2/7/15.
 */
public interface TaskUtils {
  List<TaskList> getTaskList();

  List<Task> getTaskByTaskListId(Long taskListId);

  boolean insertTask(String taskName, Long taskListId);

  void updateTask(Long id, String taskName);

  void changeTaskStatus(Long id, boolean completed);

  public static class Factory {
    private static Factory instance = new Factory();

    public static TaskUtilsImpl get(Context context) {
      return instance.newForContext(context);
    }

    public static Factory getInstance() {
      return instance;
    }

    public static void overrideInstance(Factory factory) {
      instance = factory;
    }

    protected TaskUtilsImpl newForContext(Context context) {
      return new TaskUtilsImpl(context.getContentResolver());
    }
  }
}
