import React from "react";
import { Button, Container, Table } from "reactstrap";
import Bulb from "./Bulb";

// component for displaying created rooms in table
class RoomsTable extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      Rooms: [],
      ip: "",
      isLoggedIn: false,
      id: 0,
    };

    this.enterRoom = this.enterRoom.bind(this);
    this.getIp = this.getIp.bind(this);
  }

  //update available rooms every x-ms froom server
  componentDidMount() {
    this.interval = setInterval(() => this.tick(), 1000);
    this.getIp();
  }

  componentWillUnmount() {
    //need for interval to work
    clearInterval(this.interval);
  }

  // getting rooms from server
  async tick() {
    const response = await fetch("/api/rooms");
    const body = await response.json();
    this.setState({ Rooms: body });
  }

  getIp() {
    fetch(
      "https://geolocation-db.com/json/0f761a30-fe14-11e9-b59f-e53803842572"
    )
      .then((res) => res.json())
      .then((data) => {
        this.setState({ ip: data.IPv4 });
      });
  }

  enterRoom(id) {
    this.getIp();
    this.setState({
      isLoggedIn: false,
      id: id,
    });

    fetch(`/api/check-room-by-ip/${this.state.ip}/${id}`, {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    }).then((response) => {
      let text = response.text;
      if (response.ok) {
        try {
          const data = JSON.parse(text);
          console.log(JSON.stringify(data));
        } catch (err) {
          console.log("OK");
          this.setState({ isLoggedIn: true });
        }
      } else {
        this.setState({ isLoggedIn: false });
        try {
          const data = JSON.parse(text);
          console.log(JSON.stringify(data));
        } catch (err) {
          alert("Вы не из этой страны");
        }
      }
    });
  }

  render() {
    const { Rooms } = this.state;
    const isLoggedIn = this.state.isLoggedIn;

    let rows = Rooms.map((room, counter) => (
      <tr key={room.id}>
        <td>{++counter}</td>
        <td>{room.countryCode}</td>
        <td>{room.name}</td>
        <td>
          <Button
            onClick={() => this.enterRoom(room.id)}
            size="sm"
            variant="contained"
            color="primary"
          >
            Войти
          </Button>
        </td>
      </tr>
    ));

    return (
      <Container className="my-4">
        {isLoggedIn ? <Bulb value={this.state.id} /> : <div></div>}
        <h3>Список комнат</h3>
        <Table className="mt-4">
          <thead>
            <tr>
              <th width="10%">#</th>
              <th width="30%">Страна</th>
              <th width="30%">Название комнаты</th>
              <th width="20%"></th>
            </tr>
          </thead>
          <tbody>{rows}</tbody>
        </Table>
      </Container>
    );
  }
}

export default RoomsTable;
