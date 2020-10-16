package com.qianfeng.meeting.business.controller;

import com.qianfeng.meeting.business.pojo.Dept;
import com.qianfeng.meeting.business.pojo.Worker;
import com.qianfeng.meeting.business.response.DataResult;
import com.qianfeng.meeting.business.response.ResponseCode;
import com.qianfeng.meeting.business.service.WorkerService;
import com.qianfeng.meeting.business.service.impl.WorkerServiceImpl;
import com.qianfeng.meeting.framework.annotation.QfController;
import com.qianfeng.meeting.framework.annotation.QfRequestBody;
import com.qianfeng.meeting.framework.annotation.QfRequestMapping;

import java.sql.SQLException;
import java.util.List;

@QfController("/workers")
public class WorkerController {
    private WorkerService workerService = new WorkerServiceImpl();
    @QfRequestMapping("/addWorkers")
    public DataResult addWorkers(@QfRequestBody Worker worker){
        DataResult dataResult = null;
        try {
            workerService.addWorker(worker);
            dataResult = new DataResult();
        }catch (Exception e){
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/findAllWorkers")
    public DataResult findAllWorkers(){
        DataResult dataResult = null;
        try {
            List<Worker> workerList = workerService.findAllWorkers();
            dataResult = new DataResult(workerList);
        }catch (Exception e){
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/deleteWorker")
    public DataResult deleteWorker(Integer workerId){
        DataResult dataResult = null;
        try {
            workerService.deleteWorker(workerId);
            dataResult = new DataResult();
        }catch (Exception e){
            if (e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/findAllDept")
    public DataResult findAllDept() {
        DataResult dataResult = null;
        try {
            List<Dept> deptList = workerService.findAllDept();
            dataResult = new DataResult(deptList);
        } catch (Exception e) {
            if (e instanceof SQLException) {
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            } else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }
}
