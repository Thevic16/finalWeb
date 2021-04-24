if ('undefined' === typeof window) {

  class Form {
    constructor(name, lastName, area, schoolLevel, latitude, longitude, user, photo) {
      this.name = name;
      this.lastName = lastName;
      this.area = area;
      this.schoolLevel = schoolLevel;
      this.latitude = latitude;
      this.longitude = longitude;
      this.user = user;
      this.photo = photo;
      this.status = false;
    }
  }

  importScripts('https://unpkg.com/dexie@2.0.3/dist/dexie.js');

  const indexedDB = new Dexie('dbparcial2');
  indexedDB.version(1).stores({
    forms: '++id,name,lastname,area,schoollevel,longitude,latitude,user,status,photo'
  });
  indexedDB.open().catch((err) => console.log(err));

  onmessage = (e) => {
    const option = e.data[0];

    switch (option) {
      case 'GET':
        let filteredForms = []
        indexedDB.forms.toArray().then((forms) => {
          forms.forEach((form, index) => {
            if (!form.status) {
              console.log(form);
              filteredForms.push(form);
              form.status = true;
              indexedDB.forms.update(form.id, form);
            }
          });
          console.log(forms);
          postMessage(filteredForms);
        });
        break;

        // Update the data relate to H2 DB.
      case 'UPDATE':
        console.log("Data receive UPDATE: " + e.data[1])
        let updatedForms = JSON.parse(e.data[1]) //Receiving forms from websocket
        console.log("updatedForms: " + updatedForms)
        indexedDB.forms.clear()

        updatedForms.forEach((form, index) => {
          const newForm = new Form(form.name, form.lastName, form.area, form.nivelEscolar, form.position.latitude, form.position.longitude, form.user.userName, form.photo);
          newForm.status = true;
          const result = indexedDB.forms.add(newForm);
          console.log("form created: " + result);
        });
        console.log("Udapte completed")
        break;

    }
  }
}