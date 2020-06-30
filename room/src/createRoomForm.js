import React from 'react';
import CountrySelect from './countrySelect';

class CreateRoomForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            countryCode: "",
            roomName: ""
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    //changes state if input and select was changed
    handleChange(event) {
        //uses name of event to find state with same name and set it with target value
        this.setState({ [event.target.name]: event.target.value });
    }

    handleSubmit(event) {
        alert('Your country code is: ' + this.state.countryCode + ' room name: ' + this.state.roomName);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <CountrySelect value={this.state.countryCode} onChange={this.handleChange} name="countryCode" />
                <input
                    name="roomName"
                    type="text"
                    value={this.state.roomName}
                    onChange={this.handleChange} />
                <input type="submit" value="Submit" />
            </form>
        );
    }
}

export default CreateRoomForm;