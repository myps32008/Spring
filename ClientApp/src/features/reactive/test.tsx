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
  const socket = new WebSocket('ws://localhost:3000/ws/profiles'); 
  const reactive = async () => {
    const response = await fetch('http://localhost:3000/profiles');
    const data = await response.json();
    setDataSource(data);    
    socket.addEventListener('message', async (event: any) => { 
      setDataSource(event); 
    });
  }
  useEffect(() => {
    reactive();
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