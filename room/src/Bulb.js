import React from 'react';
import './Bulb.css';

class Bulb extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      isOn: false
    };

    // This binding is necessary to make `this` work in the callback
    this.turnOn = this.turnOn.bind(this);
  }

  //changes state to opposite for bulb
  turnOn() {
    this.setState({ isOn: !this.state.isOn })
  }


  render() {

    return (
      <div>
        <i className="material-icons" style={{ fontSize: '250px', color: this.state.isOn ? '#eee12b' : '#14140f' }}>bug_report</i>

        <button
          onClick={this.turnOn}>
          {this.state.isOn ? 'выключить' : 'включить'}
        </button>
      </div>
    )
  }
}

export default Bulb;
