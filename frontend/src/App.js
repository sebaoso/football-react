import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import PlayerList from './PlayerList';
import PlayerEdit from "./PlayerEdit";
import TeamList from './TeamList';
import TeamEdit from "./TeamEdit";

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/teams' exact={true} component={TeamList}/>
            <Route path='/teams/:id' exact={true} component={TeamEdit}/>
            <Route path='/teams/:id/players' exact={true} component={PlayerList}/>
            <Route path='/teams/:team/players/:id' exact={true} component={PlayerEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;