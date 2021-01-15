import { Form, Input, Button, Checkbox, Table } from 'antd';
import Item from 'antd/lib/list/Item';
import Axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import http from '../../utils/http.client.js';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

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
  var stompClient: any = null;
  function connect() {
    var socket = new SockJS('http://localhost:8080/gs-guide-websocket');
    stompClient = Stomp.over(socket);  
    stompClient.connect({}, function(frame : any) {
        
        console.log('Connected: ' + frame);
        stompClient.subscribe('http://localhost:8080/topic/messages', (messageOutput: any) => {
          debugger;
            const data = JSON.parse(messageOutput.body);
        });
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    
    console.log("Disconnected");
}
  useEffect(() => {
    connect();
  }, [dataSource]);

  return (
    <>
      <Button onClick={() => clearInterval(interval)}>Stop create new user</Button>
      <Table dataSource={dataSource} columns={columns} />
    </>

  );
}

export default Test;