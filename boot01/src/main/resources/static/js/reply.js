/* 함수내에서 await를 사용하면
  함수에 async를 붙여준다.
  await==기다리지 않는다. 비동기
 */
async function get1(bno){
    const result = await axios.get(`/replies/list/${bno}`);

    // console.log(result);         // 1)
    // return result.data;          // 2)
    return result;                  // 3)
}