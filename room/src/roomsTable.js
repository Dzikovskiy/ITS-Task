import React from 'react';
import CountrySelect from './countrySelect';
import { Table, Container, Input, Button, Label, FormGroup, Form } from 'reactstrap';

class RoomsTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            Rooms: []
        };

    }

    //update available rooms every x-ms
    componentDidMount() {
        this.interval = setInterval(() => this.tick(), 300);
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    async tick() {
        const response = await fetch('/api/rooms');
        const body = await response.json();
        this.setState({ Rooms: body });
    }


    render() {
        const { Rooms } = this.state;

        let rows =
            Rooms.map(room =>
                <tr key={room.id}>
                    <td>{room.id}</td>
                    <td>{room.countryCode}</td>
                    <td>{room.name}</td>
                    <td><Button size="sm" variant="contained" color="primary" >Войти</Button></td>
                </tr>
            )

        return (
            < Container >
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
                    <tbody>
                        {rows}
                    </tbody>

                </Table>
            </Container >
        );
    }
}

export default RoomsTable;