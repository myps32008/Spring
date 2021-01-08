import axios from 'axios';
import Cookies from 'universal-cookie';

const base_url = process.env.NODE_ENV === "production" ? 
    "" : 
    "https://localhost:44326/";

const http_instance = axios.create({
    baseURL: `${base_url}`
});

let init = function (...inst) {    
    const cookies = new Cookies();
    const user = cookies.get("user");    
    if (user) {
        inst.forEach(ins => {
            ins.defaults.headers.common['Authorization'] = 'Bearer ' + user.token;
        });
    }
}

init(http_instance);

export default {
    internal: http_instance
}

export const ahal = (user, ...instance) => {
    if(!user && !user.token){
        instance.forEach(ins => {
            ins.defaults.headers.common['Authorization'] = 'Bearer ' + user.token;
        })
    }
}


export const rhal = (...instance) => {
    instance.forEach(ins => {
        delete ins.defaults.headers.common["Authorization"];
    })
}