
export const navItems = [
    {
      displayName: "DashBoard",
      iconName: "dashboard",
      route: "startapp/dashboard",
      children:[],
      parent:false
    },
    {
      displayName: "ArtistNote",
      iconName: "note",
      route: "startapp/note-add",
      children:[],
      parent:false
    },
    {
      displayName: "Songs",
      iconName: "library_music",
      route: "startapp/song",
      parent:false,
      children: [
        {
          displayName: "AllSongs",
          iconName: undefined,
          route: "startapp/song/all-songs",
          children:[],
          parent:true
        },
        {
          displayName: "UploadSongs",
          iconName: undefined,
          route: "startapp/song/upload",
          children:[],
          parent:true
        },
        {
          displayName: "EditSong",
          iconName: undefined,
          route:"startapp/song/edit-song",
          children:[],
          parent:true
        },
        {
          displayName: "DeleteSong",
          iconName: undefined,
          route:"startapp/song/cut-song",
          children:[] ,
          parent:true
        }
      ]
    },
    {
      displayName: "Profile",
      iconName: "face",
      route: "startapp/profile",
      parent:false,
      children: [
        {
          displayName: "EditProfile",
          iconName: undefined,
          route: "startapp/profile/edit",
          children:[],
          parent:true
        },
        {
          displayName: "ViewProfile",
          iconName: undefined,
          route: "startapp/profile/view",
          children:[],
          parent:true
        }
      ]
    },
    {
      displayName: "ContactUs",
      iconName: "contact_mail",
      route: "startapp/contact",
      children:[],
      parent:false
    },
    {
      displayName: "Logout",
      iconName: "power_settings_new",
      route: "startapp/logout",
      children:[],
      parent:false
    }
];