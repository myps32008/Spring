import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import {IUserInfo, IAppStore} from '../../const/interface';
import http from '../../utils/http.client';
import { Cookies } from 'react-cookie';
import Axios from 'axios';
import { isExpired } from 'react-jwt';

const cookies = new Cookies();
const _user = cookies.get("user"); 

const initialState: IUserInfo = _user || {
    id: 0,
    token: '',
    role: 0,
    menu: [],
    loginStatus: false,
    loading: false,
    message: ''
}
const client = http.internal
export const userLogin = createAsyncThunk('userLogin', 
  async (params:{account: string, password: string}, thunkApi) => {    
    const result = await client.post('Authen/Login', {
      params: {
        login: {
          UserName: params.account,
          Password: params.password
        }
      }
    });            
  return result.data;
});

export const testAuthen = createAsyncThunk('testAuthen', 
  async (_, {getState} :any) => {    
    const data = getState()    
    var bearer = 'Bearer ' + data.userInfo.token;    
    const config = {
      headers: { 
        'Authorization': bearer,
        'Access-Control-Allow-Origin': '*'
      }
  };
    const result = await Axios.get('https://localhost:44326/Employee/GetEmployee', config);            
  return result.data;
});

export const userInfoSlice = createSlice({
  name: 'userInfo',
  initialState,
  reducers: {    
    updateUserInfo: (state, action) => {      
      state.loginStatus = action.payload;
      cookies.set("user", state);
    },    
  },
  extraReducers: {
    'userLogin/pending': (state, action) => {      
      state.loading = true;
    },
    'userLogin/fulfilled': (state, action) => {      
      if (action.payload.token) {
        state.loginStatus = true;
      }
      state.loading = false;      
      state.token = action.payload.token;
      // cookies.set("user", state);
    },
    'userLogin/rejected': (state, action) => {
      state.loginStatus = false;
      state.loading = false;
      state.message = action.error.message;
    },
    'testAuthen/pending': (state, action) => {      
      state.loading = true;
    },
    'testAuthen/fulfilled': (state, action) => {      
      // state.loginStatus = action.payload.status;      
      // state.token = action.payload.token;
      state.loading = false;      
      // cookies.set("user", state);
    },
    'testAuthen/rejected': (state, action) => {
      state.loginStatus = false;
      state.loading = false;
      state.message = action.error.message;
    }
  }
});

export const { updateUserInfo } = userInfoSlice.actions;

export const isExistActiveToken = (state: IAppStore) => state.userInfo.token && !isExpired(state.userInfo.token);

export default userInfoSlice.reducer;
