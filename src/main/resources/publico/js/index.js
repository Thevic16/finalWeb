import Form from './form.model.js';

// indexedDB.init('dbparcial2', 1);

// console.log(indexedDB.database.result);
// addForm();
// document.getElementById('sendButton').addEventListener('click', addForm);

function addForm() {
  const form = new Form('a', 'a', 'a', 'a', 1, 1, 'a');
  Form.addForm(form);
}