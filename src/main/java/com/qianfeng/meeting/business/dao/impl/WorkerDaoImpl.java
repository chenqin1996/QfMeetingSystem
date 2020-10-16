package com.qianfeng.meeting.business.dao.impl;

import com.qianfeng.meeting.business.dao.WorkerDao;
import com.qianfeng.meeting.business.pojo.Dept;
import com.qianfeng.meeting.business.pojo.Worker;
import com.qianfeng.meeting.business.utils.QfDbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class WorkerDaoImpl implements WorkerDao {
    private QueryRunner qr = QfDbUtils.getQr();
    @Override
    public void addWorker(Worker worker) throws Exception {
        String sql ="insert into t_worker(workerName,gender,telNum,deptId) values(?,?,?,?)";
        Object[] params={worker.getWorkerName(),worker.getGender(),worker.getTelNum(),worker.getDeptId()};
        qr.update(sql,params);
    }

    @Override
    public List<Worker> findAllWorkers() throws Exception {
        String sql = "select * from t_worker";
        List<Worker> workerList = qr.query(sql, new BeanListHandler<Worker>(Worker.class));
        return workerList;
    }

    @Override
    public void deleteWorker(Integer workerId) throws Exception{
        String sql= "delete from t_worker where workerId=?";
        Object[] params = {workerId};
        qr.update(sql,params);
    }

    @Override
    public List<Dept> findAllDept() throws Exception {
        String sql = "select * from t_dept";
        List<Dept> deptList = qr.query(sql, new BeanListHandler<Dept>(Dept.class));
        return deptList;
    }
}
