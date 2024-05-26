const url = "http://localhost:8080/task/user/1";

function hideLoader() {
    document.getElementById("loading").style.display="none";
}

function show(post) {
    let tab = `<thead>
                    <th scope="col">#</th>
                    <th scope="col">Title</th>
                    <th scope="col">type</th>
                    <th scope="col">Description</th>
                    <th scope="col">Username</th>
                    <th scope="col">User id</th>
                </thead>`;

    for(let post of post){
        tab +=`
            <tr>
                <td scope="row">$ {post.id}</td>
                <td>$ {post.title}</td>
                <td>$ {post.type}</td>
                <td>$ {post.description}</td>
                <td>$ {post.user.username}</td>
                <td>$ {post.user.id}</td>
            </tr>
        `
    }

    document.getElementById("post").innerHTML = tab;
}

async function getAPI(url){
    const response = await fetch(url, { method: "GET"});

    var data = await response.json();
    console.log(data);
    if(response){
        hideLoader();
        show(data);
    }
}

getAPI(url);