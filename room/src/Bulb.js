import React from 'react';
import './Bulb.css';
import { Table, Container, Input, Button, Label, FormGroup, Form } from 'reactstrap';

class Bulb extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      id: 4,
      isOn: false
    };

    // This binding is necessary to make `this` work in the callback
    this.turnOn = this.turnOn.bind(this);
  }

  //changes state to opposite for bulb
  turnOn() {
    //uses callback only after state update to run function
    this.setState({ isOn: !this.state.isOn }, () => this.updateBulbStateOnServer());
  }

  updateBulbStateOnServer() {
    let data = {
      id: this.state.id,
      bulbState: this.state.isOn
    }

    //save new bulb state to database with rest api
    fetch('/api/room', {
      method: 'PUT',
      body: JSON.stringify(data),
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(response => response.json()).then(data => alert(JSON.stringify(data)));
  }

  render() {

    return (
      <Container maxWidth="sm" className="my-4">
        <div style={{ border: '4px solid black' }}>
          <i className="material-icons" style={{ fontSize: '250px', color: this.state.isOn ? '#eee12b' : '#14140f' }}>bug_report</i>

          <Button color="warning"
            onClick={this.turnOn}>
            {this.state.isOn ? 'выключить' : 'включить'}
          </Button>
        </div>
      </Container>
    )
  }
}

export default Bulb;
