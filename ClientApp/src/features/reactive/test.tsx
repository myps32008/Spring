import { Form, Input, Button, Checkbox, Table } from 'antd';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import http from '../../utils/http.client.js';

const Test = (props: any) => {
  const dispatch = useDispatch();
  const [form] = Form.useForm();
  const interval = setInterval(() => {

  }, 1000);

  const [dataSource, setDataSource] = useState([]);

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Address',
      dataIndex: 'address',
      key: 'address',
    },
  ];
  useEffect(() => {
    http.internal
      .get("/api/users/getAll")
      .then(result => {
        setDataSource(result.data);
      });
  }, []);

  return (
    <>
      <Button onClick={() => clearInterval(interval)}>Stop create new user</Button>
      <Table dataSource={dataSource} columns={columns} />
    </>

  );
}

export default Test;