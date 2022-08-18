package com.zods.workflow.modules.task.controller;
import com.zods.workflow.common.ResponseBean;
import com.zods.workflow.modules.task.controller.dto.TaskInfo;
import com.zods.workflow.modules.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
/**
 * @desc 任务管理接口
 * @author jianglong
 * @date 2022-08-09
 **/
@Api(value = "待办任务接口")
@Controller
@RequestMapping("/task/manage")
public class TaskController  {

    @Autowired
    private TaskService taskServiceImpl;

    /**
     * 查询我的待办任务列表
     */
    @ApiOperation("查询我的待办任务列表")
    @PostMapping("/mylist")
    @ResponseBody
    public ResponseBean mylist(TaskInfo param) {
        return taskServiceImpl.mylist(param);
    }

    /**
     * 查询所有待办任务列表
     */
    @ApiOperation("查询所有待办任务列表")
    @PostMapping("/alllist")
    @ResponseBody
    public ResponseBean alllist(TaskInfo param) {
         return taskServiceImpl.alllist(param);
    }

    /**
     * 用taskid查询formkey
     **/
    @ApiOperation("用taskid查询formkey")
    @PostMapping("/forminfo/{taskId}")
    @ResponseBody
    public ResponseBean alllist(@PathVariable String taskId) {
       return taskServiceImpl.alllist(taskId);
    }

    @ApiOperation("办理一个用户任务")
    @RequestMapping(value = "/completeTask/{taskId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean completeTask(@PathVariable("taskId") String taskId, @RequestBody(required=false) Map<String, Object> variables) {
        return  taskServiceImpl.completeTask(taskId,variables);
    }

    @ApiOperation("任务办理时间轴")
    @RequestMapping(value = "/history/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public List<TaskInfo> history(@PathVariable String taskId) {
        return  taskServiceImpl.history(taskId);
    }
}
