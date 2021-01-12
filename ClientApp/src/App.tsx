import React from 'react';
import "antd/dist/antd.less";
import 'antd/dist/antd.css';
import {
  BrowserRouter as Router,
  Switch,
  Route,  
  Redirect, 
} from "react-router-dom";
import { useSelector } from 'react-redux';
import LoginForm from './features/login/login';
import {isExistActiveToken} from './features/login/loginSlice';
import Test from './features/reactive/test';

function App() {
  return (
    <>      
        <Router>        
          <Switch>        
            <Route path="/login" component={LoginForm}/>            
            <RouteAuthen>
              <Route path="/" component={Test}/>
            </RouteAuthen>
          </Switch>          
        </Router>
    </>    
  );
}

function RouteAuthen({children, ...rest}:any) {    
  // const isAuthen = useSelector(isExistActiveToken);
  const isAuthen = true;
  return (
    <Route
      {...rest}
      render=
        {
          ({ location }) =>
          isAuthen ? (children) : (
              <Redirect
                to={{
                  pathname: "/login",
                  state: { from: location }
                }}
              />
            )
        }
    />
  );
}
export default App;
