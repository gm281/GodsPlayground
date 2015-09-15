var Draggable = ReactDraggable;

var App = React.createClass({
  getInitialState: function () {
    return {
      position: {
        top: 0, left: 0
      },
    };
  },

  handleDrag: function (e, ui) {
    this.setState({
      position: ui.position
    });
  },

  onStart: function() {
    console.log("Start");
  },

  onStop: function() {
    console.log("Stop");
  },

  handleAvatarClick: function() {
    console.log("Click");
    var avatarLeft = this.state.avatarLeft;
    if (avatarLeft == undefined) {
       avatarLeft = 117;
    } else {
       avatarLeft += 100;
    }
    console.log(avatarLeft);
    this.setState({avatarLeft: avatarLeft});
  },

  render: function () {
    var drags = {onStart: this.onStart, onStop: this.onStop};
    var windowWidth = window.innerWidth;
    var avatarLeft = this.state.avatarLeft;
    return (
      <div className="whole">
       <Menu ref="right" alignment="right">
          <MenuItem hash="first-page">First Page</MenuItem>
          <MenuItem hash="second-page">Second Page</MenuItem>
          <MenuItem hash="third-page">Third Page</MenuItem>
      </Menu>
       <div className="map" onClick={console.log("hello map")}>
         <Draggable zIndex={100} {...drags} bounds="parent-outside" onClick={this.onClick}>
  		 <div>
           	<div><img draggable="false" src="images/earth.png" /></div>
  		 	<div className="avatar" style={{ left: avatarLeft+"px", top: "300px" }} onClick={this.handleAvatarClick}><img draggable="false" src="images/balloon.png" /></div>
  		 </div>
         </Draggable>
       </div>
      </div>
    );
  }
});

var Menu = React.createClass({
    getInitialState: function() {
        return {
            visible: true
        };
    },

    show: function() {
        this.setState({ visible: true });
        document.addEventListener("click", this.hide.bind(this));
    },

    hide: function() {
        document.removeEventListener("click", this.hide.bind(this));
        this.setState({ visible: false });
    },

    render: function() {
        return <div className="menu">
            <div className={(this.state.visible ? "visible " : "") + this.props.alignment}>{this.props.children}</div>
        </div>;
    }
});

var MenuItem = React.createClass({
    navigate: function(hash) {
        window.location.hash = hash;
    },

    render: function() {
        return <div className="menu-item" onClick={this.navigate.bind(this, this.props.hash)}>{this.props.children}</div>;
    }
});
