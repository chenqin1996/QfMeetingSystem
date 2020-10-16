package com.qianfeng.meeting.business.dao;

import com.qianfeng.meeting.business.pojo.Dept;
import com.qianfeng.meeting.business.pojo.Worker;

import java.util.List;

public interface WorkerDao {
    void addWorker(Worker worker) throws Exception;

    List<Worker> findAllWorkers() throws Exception;

    void deleteWorker(Integer workerId) throws Exception;

    List<Dept> findAllDept()throws Exception;
}
