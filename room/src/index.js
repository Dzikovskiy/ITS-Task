import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Bulb from './Bulb';
import CreateRoomForm from './createRoomForm';
import 'bootstrap/dist/css/bootstrap.min.css';
import RoomsTable from './roomsTable';

// put components in 'root' class of index.html
ReactDOM.render(
  <div>
    <CreateRoomForm />
    <Bulb />
    <RoomsTable/>
  </div>
  ,
  document.getElementById('root')
);
