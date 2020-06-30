import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Bulb from './Bulb';
import CreateRoomForm from './createRoomForm';

// put components in 'root' class of index.html
ReactDOM.render(
  <div>
    <CreateRoomForm />
    <Bulb />
  </div>
  ,
  document.getElementById('root')
);
