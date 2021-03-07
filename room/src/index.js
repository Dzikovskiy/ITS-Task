import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import ReactDOM from "react-dom";
import CreateRoomForm from "./createRoomForm";
import "./index.css";
import RoomsTable from "./roomsTable";

// put components in 'root' class of index.html
ReactDOM.render(
  <div>
    <CreateRoomForm />
    <RoomsTable />
  </div>,
  document.getElementById("root")
);
