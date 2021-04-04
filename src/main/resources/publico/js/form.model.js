import indexedDB from './database.js'


export default class Form {

  constructor(name, lastName, area, schoolLevel, latitude, longitude, user) {
    this.name = name;
    this.lastName = lastName;
    this.area = area;
    this.schoolLevel = schoolLevel;
    this.latitude = latitude;
    this.longitude = longitude;
    this.user = user;
  }

  static addForm(form) {
    // const activeConn = indexedDB.database.result;
    // console.log(activeConn);
    // let transaction = activeConn.transaction(['forms'], 'readwrite');

    // let forms = transaction.objectStore('forms');
    // let request = forms.put({
    //   name: this.name,
    //   lastName: this.lastName,
    //   area: this.lastName,
    //   schoolLevel: this.schoolLevel,
    //   latitude: this.latitude,
    //   longitude: this.longitude,
    //   user: this.user
    // });

    // //Manejando los errores.
    // transaccion.onerror = function (e) {
    //   alert(e.error.name + ': ' + e.error.message);
    // };

    // transaccion.oncomplete = function (e) {
    //   alert('Objeto agregado correctamente');
    // };

    // request.onerror = function (e) {
    //   let mensaje = "Error: " + e.target.errorCode;
    //   console.error(mensaje);
    //   alert(mensaje)
    // };

    // request.onsuccess = function (e) {
    //   console.log("Datos Procesado con exito");
    //   indexedDB.database.close();
    // };
    indexedDB.transaction(rw, db.forms, () => {
      db.forms.add(form);
    }).catch((e) => console.log(e));
  }

}