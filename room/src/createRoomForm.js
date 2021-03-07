import React from "react";
import { Button, Container, Form, FormGroup, Input, Label } from "reactstrap";
import CountrySelect from "./countrySelect";

// component with form for creating new room
class CreateRoomForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      countryCode: "AX",
      roomName: "",
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  //changes state if input and select was changed
  handleChange(event) {
    //uses name of event to find state with same name and set it with target value
    this.setState({ [event.target.name]: event.target.value });
  }

  async handleSubmit(event) {
    let data = {
      countryCode: this.state.countryCode,
      name: this.state.roomName.trim()
    };
    //save room to database with rest api
    fetch("/api/rooms", {
      method: "POST",
      body: JSON.stringify(data),
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    });
    event.preventDefault();
  }

  render() {
    return (
      <div>
        <Container maxwidth="sm" className="my-4">
          <Form onSubmit={this.handleSubmit}>
            <FormGroup className="col-md-3 mb-3">
              <Label for="exampleEmail">Название страны:</Label>
              <CountrySelect
                value={this.state.countryCode}
                onChange={this.handleChange}
                name="countryCode"
              />
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Label for="exampleEmail">Название вашей комнаты:</Label>
              <Input
                name="roomName"
                type="text"
                value={this.state.roomName}
                onChange={this.handleChange}
                required
              />
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Button variant="contained" color="primary" type="submit">
                Создать команту
              </Button>
            </FormGroup>
          </Form>
        </Container>
      </div>
    );
  }
}

export default CreateRoomForm;
