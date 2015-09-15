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
  },

  render: function () {
    var drags = {onStart: this.onStart, onStop: this.onStop};
    var windowWidth = window.innerWidth;
	var gameplay = this.state.gameplay;
	var mapImage = "";
	if (gameplay != undefined) {
		mapImage = gameplay.mapImage;
	}
	var avatars = this.state.avatars;
	if (avatars == undefined) {
		avatars = [];
	}

    return (
      <div className="whole">
       <Menu ref="right" alignment="right">
          <MenuItem hash="Add">Add avatar</MenuItem>
          <MenuItem hash="Del">Remove avatar</MenuItem>
      </Menu>
       <div className="map">
         <Draggable zIndex={100} {...drags} bounds="parent-outside" onClick={this.onClick}>
  		 <div>
           	<div><img draggable="false" src={mapImage} /></div>
			{
				avatars.map(function(avatar, i) {
					return <div className="avatar" style={{ left: avatar["xPosition"]+"px", top: avatar["yPosition"]+"px", zIndex: avatar["id"]}} onClick={this.handleAvatarClick}><img draggable="false" src="images/balloon.png" /></div>;
    			})
			}
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
		if (hash == "Add") {
			op = true;
		} else {
			op = false;
		}
		modAvatars(op);
    },

    render: function() {
        return <div className="menu-item" onClick={this.navigate.bind(this, this.props.hash)}>{this.props.children}</div>;
    }
});
