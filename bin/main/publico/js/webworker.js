if ('undefined' === typeof window) {
  importScripts('https://unpkg.com/dexie@2.0.3/dist/dexie.js');

  const indexedDB = new Dexie('dbparcial2');
  indexedDB.version(1).stores({
    forms: '++id,name,lastname,area,schoollevel,longitude,latitude,user,status'
  });
  indexedDB.open().catch((err) => console.log(err));

  onmessage = (e) => {
    const option = e.data;

    switch (option) {
      case 'GET':
        indexedDB.forms.toArray().then((e) => {
          console.log(e);
          postMessage(e);
        });
    }
  }
}