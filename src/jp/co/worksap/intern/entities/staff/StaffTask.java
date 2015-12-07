package jp.co.worksap.intern.entities.staff;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class StaffTask implements Serializable{

    /*
    *   task_id
  request_id
  staff_id
  task_start_time
  task_state*/

    private Integer taskId;
    private Integer requestId;
    private Integer staffId;
    private Date taskStartTime,taskEndTime;
    private String taskState;
    private String desc;

    public StaffTask() {
    }

    public StaffTask(Integer staffId, Date taskStartTime, Date taskEndTime, String taskState,String desc) {
        this.staffId = staffId;
        this.taskStartTime = taskStartTime;
        this.taskEndTime = taskEndTime;
        this.taskState = taskState;
        this.desc = desc;
    }


	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
