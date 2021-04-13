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
    }
  }
}