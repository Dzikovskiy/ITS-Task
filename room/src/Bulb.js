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
    fetch(`/api/room/${this.state.id}`, {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    })
      .then((res) => res.json())
      .then((data) => {
        this.setState({ isOn: data.bulbState });
      });
  }

  //update bulb state on server after button click
  updateBulbStateOnServer() {
    let data = {
      id: this.state.id,
      bulbState: this.state.isOn,
    };

    //save new bulb state to database with rest api
    fetch(`/api/room/${this.state.id}`, {
      method: "PUT",
      body: JSON.stringify(data),
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
          // JSON handling here
        } catch (err) {
          // It is text
          console.log("OK");
        }
      } else {
        try {
          const data = JSON.parse(text);
          console.log(JSON.stringify(data));
        } catch (err) {
          console.log("Not found");
        }
      }
    });
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
