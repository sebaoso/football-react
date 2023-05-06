import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class TeamList extends Component {

    constructor(props) {
        super(props);
        this.state = {teams: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/teams')
            .then(response => response.json())
            .then(data => this.setState({teams: data}));
    }

    async remove(id) {
        await fetch(`/teams/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedTeams = [...this.state.teams].filter(i => i.id !== id);
            this.setState({players: updatedTeams});
        });
    }

    render() {
        const {teams} = this.state;

        const teamList = teams.map(team => {
            return <tr key={team.id}>
                <td style={{whiteSpace: 'nowrap'}}><Link to={"/teams/"+ team.id + "/players"}>{team.name}</Link></td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/teams/" + team.id}>Edit</Button>
                        <Button size="sm" color="secondary" onClick={() => this.remove(team.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="primary" tag={Link} to="/teams/new">Add Team</Button>
                    </div>
                    <h3>Teams</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="60%">Name</th>
                            <th width="40%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {teamList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default TeamList;