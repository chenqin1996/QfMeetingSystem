package com.qianfeng.meeting.business.service.impl;

import com.qianfeng.meeting.business.dao.WorkerDao;
import com.qianfeng.meeting.business.dao.impl.WorkerDaoImpl;
import com.qianfeng.meeting.business.pojo.Dept;
import com.qianfeng.meeting.business.pojo.Worker;
import com.qianfeng.meeting.business.service.WorkerService;

import java.util.List;

public class WorkerServiceImpl implements WorkerService {
    private WorkerDao workerDao = new WorkerDaoImpl();
    @Override
    public void addWorker(Worker worker) throws Exception {
        workerDao.addWorker(worker);
    }

    @Override
    public List<Worker> findAllWorkers() throws Exception {
        return workerDao.findAllWorkers();
    }

    @Override
    public void deleteWorker(Integer workerId) throws Exception {
        workerDao.deleteWorker(workerId);
    }

    @Override
    public List<Dept> findAllDept() throws Exception {
        return workerDao.findAllDept();
    }
}
