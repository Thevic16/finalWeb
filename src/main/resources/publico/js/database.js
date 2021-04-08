// class IndexedDB {
//   constructor() {
//     this.IndexedDBReference = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB;
//     this.database = this.IndexedDBReference;
//   }

//   init(dbname, version) {
//     this.database.open(dbname, version);

//     this.database.onupgradeneeded = (e) => {
//       console.log("Creating datbase structure...");
//       activeConn = this.database.result;


//       activeConn.createObjectStore('forms', {
//         keyPath: 'id',
//         autoIncrement: true
//       });
//     }

//     this.database.onsucess = (e) => {
//       console.log("Database created correctly");
//     }

//     this.database.onerror = (e) => {
//       console.log("Error: " + e.target.errorCode);
//     }

//   }
// }
// const indexedDB = new IndexedDB();
// export default indexedDB;
import Dexie from '../../../../../node_modules/dexie/dist/dexie'
const indexedDB = new Dexie('dbparcial2');
indexedDB.version(1).stores({
  forms: '++id,name,lastname,area,schoollevel,longitude,latitude,user'
});

console.log(indexedDB);

export default indexedDB