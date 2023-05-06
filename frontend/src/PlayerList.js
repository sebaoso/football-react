import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class PlayerList extends Component {

    constructor(props) {
        super(props);
        this.state = {players: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch(`/teams/${this.props.match.params.id}/players`)
            .then(response => response.json())
            .then(data => this.setState({players: data}));
    }

    async remove(id) {
        await fetch(`/players/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedPlayers = [...this.state.players].filter(i => i.id !== id);
            this.setState({players: updatedPlayers});
        });
    }

    render() {
        const {players} = this.state;

        const playerList = players.map(player => {
            return <tr key={player.id}>
                <td style={{whiteSpace: 'nowrap'}}>{player.name}</td>
                <td>{player.position}</td>
                <td>{player.club}</td>
                <td>{player.team}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/teams/"+player.team+"/players/" + player.id}>Edit</Button>
                        <Button size="sm" color="secondary" onClick={() => this.remove(player.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="primary" tag={Link} to={"/teams/"+this.props.match.params.id+"/players/new"}>Add Player</Button>
                    </div>
                    <h3>Players</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Position</th>
                            <th width="20%">Club</th>
                            <th width="20%">Team</th>
                            <th width="20%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {playerList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default PlayerList;