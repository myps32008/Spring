import { Form, Input, Button, Checkbox, Table } from 'antd';
import Item from 'antd/lib/list/Item';
import Axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import http from '../../utils/http.client.js';

const Test = (props: any) => {
  const dispatch = useDispatch();
  const [form] = Form.useForm();
  const interval = setInterval(() => {

  }, 1000);

  const [dataSource, setDataSource] = useState([]);

  const columns: any = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: 'Name',
      dataIndex: 'username',
      key: 'username',
    },
    {
      title: 'Address',
      dataIndex: 'email',
      key: 'email',
    },
    ,
    {
      title: 'operation',
      dataIndex: 'operation',
      render: (text: String, record: any, index: number) => {
        return <><Button onClick={() => handleDelete(record.id)} key={record.id}>Delete</Button></>
      },
    },
  ];
  const handleDelete = async (id: number) => {
    const result = await Axios.get('https://localhost:8080/api/users/delete/' + id)
    debugger    
  }
  const socket = new WebSocket('ws://localhost:8080/ws/api/users/stream');
  const reactive = async () => {
    // const eventSource = new EventSource('http://localhost:8080/api/users/stream');
    // eventSource.onopen = (event: any) => console.log('open', event);
    // eventSource.onmessage = (event: any) => {
    //   const data = JSON.parse(event.data);
    //   const count = data.length;
    //   for (let index = 0; index < count; index++) {
    //     data[index].key = data[index].id;        
    //   }
    //   // debugger;      
    //   setDataSource(data);
    // };
    // eventSource.onerror = (event: any) => {
    //   console.log('error', event)
    // };       
    socket.addEventListener('message', async (event: any) => {
      debugger;
      setDataSource(event);
    });    
  }
  useEffect(() => {
    reactive();
  }, [dataSource]);

  return (
    <>
      <Button onClick={() => clearInterval(interval)}>Stop create new user</Button>
      <Table dataSource={dataSource} columns={columns} />
    </>

  );
}

export default Test;