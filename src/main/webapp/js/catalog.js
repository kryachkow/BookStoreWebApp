$(document).ready(function () {
  const entries = new URLSearchParams(window.location.search).entries();


  function redeployParameters(elem, list) {
    for (const entry of entries) {
      if (list.indexOf(entry[0]) === -1) {
        $('<input />').attr('type', 'hidden')
        .attr('name', entry[0])
        .attr('value', entry[1])
        .appendTo(elem);
      }
    }

  }

  $('.addToCartForm').submit(function () {
    redeployParameters(this, [])
  })

  $('.pageForm').submit(function () {
    redeployParameters(this, ['pageNumber'])
  })


  $('#pageSize').change(function () {
    redeployParameters(this.form, ['pageNumber', 'pageSize'])
    this.form.submit();
  })

  $('#sorting').change(function () {
    redeployParameters(this.form, ['pageNumber', 'sorting'])
    this.form.submit();
  })

  $('#inverted').change(function () {
    redeployParameters(this.form, ['pageNumber', 'inverted'])
    this.form.submit();
  })

})