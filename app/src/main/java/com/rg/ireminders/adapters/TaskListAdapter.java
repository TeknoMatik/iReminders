package com.rg.ireminders.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import com.rg.ireminders.R;
import com.rg.ireminders.db.utils.TaskUtils;
import com.rg.ireminders.db.utils.impl.TaskUtilsImpl;
import com.rg.ireminders.utils.DateUtils;
import org.dmfs.provider.tasks.TaskContract;

public class TaskListAdapter extends ResourceCursorAdapter {
  private Context mContext;
  private int mColor;

  private View.OnKeyListener mEditTextKeyListener = new View.OnKeyListener() {
    @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
      if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
        String title = ((EditText) v).getText().toString();
        Long id = (Long) v.getTag();
        TaskUtils.Factory.get(mContext).updateTask(id, title);
        return true;
      }
      return false;
    }
  };

  private View.OnClickListener mOnClickListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      v.setFocusable(true);
    }
  };

  private CompoundButton.OnCheckedChangeListener mOnCheckedChangedListener =
      new CompoundButton.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          Long id = (Long) buttonView.getTag();
          TaskUtilsImpl.Factory.get(mContext).changeTaskStatus(id, isChecked);
        }
      };

  public TaskListAdapter(Context context, int layout, Cursor c, int flags, int color) {
    super(context, layout, c, flags);
    mContext = context;
    mColor = color;
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    RadioButton statusRadioButton = (RadioButton) view.findViewById(R.id.radioButton);
    EditText titleEditText = (EditText) view.findViewById(R.id.radioButtonText);
    TextView dueText = (TextView) view.findViewById(R.id.dueTextView);

    String title = cursor.getString(cursor.getColumnIndex(TaskContract.TaskColumns.TITLE));
    Integer status = cursor.getInt(cursor.getColumnIndex(TaskContract.TaskColumns.STATUS));
    Long due = cursor.getLong(cursor.getColumnIndex(TaskContract.TaskColumns.DUE));
    Long id = cursor.getLong(cursor.getColumnIndex(TaskContract.TaskColumns._ID));

    statusRadioButton.setChecked(status != TaskContract.TaskColumns.STATUS_NEEDS_ACTION);
    statusRadioButton.setTag(id);
    statusRadioButton.setOnCheckedChangeListener(mOnCheckedChangedListener);
    setRadioButtonColor(statusRadioButton);

    titleEditText.setText(title);
    titleEditText.setTag(id);
    titleEditText.setOnKeyListener(mEditTextKeyListener);
    titleEditText.setOnClickListener(mOnClickListener);

    if (due == 0 || status == TaskContract.TaskColumns.STATUS_COMPLETED) {
      dueText.setVisibility(View.GONE);
    } else {
      String date = DateUtils.getDueDate(due);
      dueText.setVisibility(View.VISIBLE);
      dueText.setText(date);
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void setRadioButtonColor(RadioButton statusRadioButton) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      statusRadioButton.setButtonTintList(ColorStateList.valueOf(mColor));
    }
  }
}
