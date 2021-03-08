import ky from "ky";
import React from "react";
import { Button, Container } from "reactstrap";
import "./Bulb.css";

// component for displaying bulb state
class Bulb extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      id: props.value,
      isOn: false,
      countryCode: "",
      name: "",
    };

    // This binding is necessary to make `this` work in the callback
    this.turnOnOff = this.turnOnOff.bind(this);
    this.updateBulbStateOnServer = this.updateBulbStateOnServer.bind(this);
  }

  //changes state to opposite for bulb
  turnOnOff() {
    //uses callback only after state update to run function
    this.setState({ isOn: !this.state.isOn }, () =>
      this.updateBulbStateOnServer()
    );
  }

  //updates bulb state from server every x-ms
  componentDidMount() {
    this.tick();
    this.interval = setInterval(() => this.tick(), 1200);
  }

  componentWillUnmount() {
    //need for interval to work
    clearInterval(this.interval);
  }

  // getting bulb state from server
  async tick() {
    ky.get(`/api/rooms/${this.state.id}`)
      .json()
      .then((data) => {
        this.setState({
          isOn: data.bulbState,
          name: data.name,
          countryCode: data.countryCode,
        });
      });
  }

  //update bulb state on server after button click
  async updateBulbStateOnServer() {
    let data = {
      id: this.state.id,
      bulbState: this.state.isOn,
      name: this.state.name,
      countryCode: this.state.countryCode,
    };

    //save new bulb state to database with rest api
    let response = await ky.put(`/api/rooms/${this.state.id}`, {
      json: data,
      throwHttpErrors: false,
    });

    console.log("response.status = ", response.status);
  }

  render() {
    return (
      <div>
        <Container maxwidth="sm" className="my-4">
          <div style={{ border: "4px solid black" }}>
            <i
              className="material-icons"
              style={{
                fontSize: "250px",
                color: this.state.isOn ? "#eee12b" : "#14140f",
              }}
            >
              emoji_objects
            </i>

            <Button color="warning" className="my-4" onClick={this.turnOnOff}>
              {this.state.isOn ? "выключить" : "включить"}
            </Button>
          </div>
        </Container>
      </div>
    );
  }
}

export default Bulb;
