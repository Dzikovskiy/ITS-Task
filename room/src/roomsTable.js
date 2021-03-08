import ky from "ky";
import React from "react";
import { Button, Container, Table } from "reactstrap";
import Bulb from "./Bulb";

// component for displaying created rooms in table
class RoomsTable extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      Rooms: [],
      isLoggedIn: false,
      id: 0,
    };
    this.enterRoom = this.enterRoom.bind(this);
  }

  //update available rooms every x-ms from server
  componentDidMount() {
    this.interval = setInterval(() => this.tick(), 1000);
  }

  componentWillUnmount() {
    //need for interval to work
    clearInterval(this.interval);
  }

  // getting rooms from server
  async tick() {
    const rooms = await ky.get("/api/rooms").json();
    this.setState({ Rooms: rooms });
  }

  async enterRoom(roomId) {
    await this.setState({
      isLoggedIn: false,
      id: roomId,
    });

    ky.get(`/api/rooms/${this.state.id}`, { throwHttpErrors: false }).then(
      (response) => {
        if (response.ok) {
          this.setState({ isLoggedIn: true });
        } else if (response.status === 403) {
          this.setState({ isLoggedIn: false });
          alert("Вы не из этой страны");
        } else {
          this.setState({ isLoggedIn: false });
          alert("Комната не найдена");
        }
      }
    );
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
